import { Component, OnInit } from '@angular/core';
import { NbToastrService } from '@nebular/theme';
import { ChartDataSets, ChartOptions } from 'chart.js';
import * as moment from 'moment';
import { Color, Label } from 'ng2-charts';
import { Sprint } from 'src/app/entity/sprint';
import { ReportsService } from 'src/app/service/reports.service';
import { SprintService } from 'src/app/service/sprint.service';
import { StateService } from 'src/app/service/state.service';

@Component({
  selector: 'app-burndown-chart',
  templateUrl: './burndown-chart.component.html',
  styleUrls: ['./burndown-chart.component.css']
})
export class BurndownChartComponent implements OnInit {
  total: number;
  totalTasks: number;
  chartData: number;
  activeSprintDetails: any;
  burndownChartData: any = [];
  sprintID: any;
  projectID: any;
  burndownChartDate = new Date();
  lineChartData: ChartDataSets[] = [
    {
      data: [],
      label: 'Ideal'
    },
    {
      data: [],
      label: 'Actual'
    },
  ];
  lineChartLabels: Label[] = [];
  lineChartOptions: ChartOptions = {
    responsive: true,
  };
  lineChartLegend = false;
  lineChartType = 'line';
  lineChartPlugins = [];
  lineChartColors: Color[] = [
    {
      backgroundColor: 'transparent',
      borderColor: '#00d68f',
    },
    {
      backgroundColor: 'transparent',
      borderColor: '#BC2550',
    },
  ];

  constructor(
    private reportsService: ReportsService,
    private sprintService: SprintService,
    private stateService: StateService,
    private toastrService: NbToastrService
  ) { }

  ngOnInit() {
    // this.stateService.selectedProjectId.subscribe((data) => {
    //   this.projectID = Number(data);
    //   this.sprintService.getActiveSprintByProject(this.projectID).subscribe((data) => {
    //     if (data) {
    //       this.activeSprintDetails = data[0];
    //     }
    //   })
    // });
    // this.stateService.selectedSprintId.subscribe((data) => {
    //   this.sprintID = Number(data);
    //   if (this.sprintID !== 0) {
    //     this.reportsService.getBySprintID(this.sprintID).subscribe((data) => {
    //       if (data) {
    //         this.burndownChartData.push(data);
    //         if (this.burndownChartData.length !== 0) {
    //           this.burndownChartData[0].perfectLine.forEach(element => {
    //             if (element) {
    //               this.lineChartData[0].data.push(element.value);
    //               const momentVariable = moment(element.recordedAt, 'DD-MM-YYYY');
    //               const stringvalue = momentVariable.format('YYYY-MM-DD');
    //               this.lineChartLabels.push(stringvalue);
    //             }
    //           });
    //           this.burndownChartData[0].actualLine.forEach(element => {
    //             if (element) {
    //               this.lineChartData[1].data.push(element.value);
    //               // this.lineChartLabels.push(element.recordedAt);
    //             }
    //           });
    //         }
    //       }
    //     });
    //   } else {
    //   }
    // });

    this.stateService.selectedProjectId.subscribe((data) => {
      this.projectID = Number(data);
      this.sprintService.getActiveSprintByProject(this.projectID).subscribe((activeSprintData) => {
        if (activeSprintData && activeSprintData[0]) {
          this.activeSprintDetails = activeSprintData;
          console.log(this.activeSprintDetails)
          this.sprintService.getProgressbar(activeSprintData[0].id).subscribe((progressData) => {
            this.total = progressData.todo + progressData.inprogress + progressData.completed;
          });
          this.sprintService.getCompletedTaskCount(activeSprintData[0].id).subscribe((completeData) => {
            this.totalTasks =  completeData.completed;
          });
        }
      })
    });

    this.stateService.selectedSprintId.subscribe((data) => {
      this.sprintID = Number(data);
      this.burndownChartData = [];
      this.lineChartData[0].data = [];
      this.lineChartData[1].data = [];
      this.lineChartLabels = [];
      if (this.sprintID !== 0) {
        this.reportsService.getBySprintID(this.sprintID).subscribe((reportData) => {
          if (reportData) {
            this.burndownChartData.push(reportData);
            if (this.burndownChartData.length !== 0) {
              this.burndownChartData[0].perfectLine.forEach(element => {
                if (element) {
                  this.lineChartData[0].data.push(element.value);
                  const momentVariable = moment(element.recordedAt, 'DD-MM-YYYY');
                  const stringvalue = momentVariable.format('YYYY-MM-DD');
                  this.lineChartLabels.push(stringvalue);
                }
              });
              this.burndownChartData[0].actualLine.forEach(element => {
                if (element) {
                  this.lineChartData[1].data.push(element.value);
                  // this.lineChartLabels.push(element.recordedAt);
                }
              });
            }
          }
        });
      } else {
      }
    });
  }

  chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
  }

}
